(function() {
	AUI.add(
		'liferay-plugin-meeting-scheduler',
		function(A) {
			var EVENT_SELECTION_CHANGE = 'selectionChange';

			var MeetingScheduler = A.Component.create(
				{
					AUGMENTS: [Liferay.PortletBase],

					EXTENDS: A.Base,

					NAME: 'meetingscheduler',

					prototype: {
						initializer: function(config) {
							var instance = this;

							instance._containerNode = instance.byId(config.containerId);
							instance._submitButtonNode = instance.byId(config.submitButtonId);

							instance._duration = 0;
							instance._endDate = new Date();
							instance._startDate = new Date();
							instance._validDate = true;

							instance._endDatePicker = instance._getComponent(config.endDatePickerName + 'DatePicker');
							instance._endTimePicker = instance._getComponent(config.endTimePickerName + 'TimePicker');
							instance._startDatePicker = instance._getComponent(config.startDatePickerName + 'DatePicker');
							instance._startTimePicker = instance._getComponent(config.startTimePickerName + 'TimePicker');

							instance._initPicker(instance._endDatePicker);
							instance._initPicker(instance._endTimePicker);
							instance._initPicker(instance._startDatePicker);
							instance._initPicker(instance._startTimePicker);

							instance._setEndDate();
							instance._setEndTime();
							instance._setStartDate();
							instance._setStartTime();
							instance._setDuration();

							instance.bindUI();
						},

						bindUI: function() {
							var instance = this;

							instance._endDatePicker.on(
								EVENT_SELECTION_CHANGE,
								function() {
									instance._setEndDate();

									if (instance._validDate && (instance._startDate.valueOf() >= instance._endDate.valueOf())) {
										instance._startDate = new Date(instance._endDate.valueOf() - instance._duration);

										instance._setStartDatePickerDate();
									}

									instance._setDuration();
									instance._validate();
								}
							);

							instance._endTimePicker.on(
								EVENT_SELECTION_CHANGE,
								function() {
									instance._setEndTime();

									if (instance._validDate && (instance._startDate.valueOf() >= instance._endDate.valueOf())) {
										instance._startDate = new Date(instance._endDate.valueOf() - instance._duration);

										instance._setStartDatePickerDate();
										instance._setStartTimePickerTime();
									}

									instance._setDuration();
									instance._validate();
								}
							);

							instance._startDatePicker.on(
								EVENT_SELECTION_CHANGE,
								function() {
									instance._setStartDate();

									if (instance._validDate) {
										instance._endDate = new Date(instance._startDate.valueOf() + instance._duration);

										instance._setEndDatePickerDate();
									}

									instance._setDuration();
									instance._validate();
								}
							);

							instance._startTimePicker.on(
								EVENT_SELECTION_CHANGE,
								function() {
									instance._setStartTime();

									if (instance._validDate) {
										instance._endDate = new Date(instance._startDate.valueOf() + instance._duration);

										instance._setEndDatePickerDate();
										instance._setEndTimePickerTime();
									}

									instance._setDuration();
									instance._validate();
								}
							);
						},

						_getComponent: function(name) {
							var instance = this;

							return Liferay.component(instance.NS + name);
						},

						_initPicker: function(picker) {
							var instance = this;

							var attrs = picker.getAttrs();

							var inputNode = A.one(attrs.container._node.children[0]);

							picker.useInputNodeOnce(inputNode);
						},

						_setDuration: function() {
							var instance = this;

							instance._duration = (instance._endDate.valueOf() - instance._startDate.valueOf());
						},

						_setEndDate: function() {
							var instance = this;

							var endDate = instance._endDatePicker.getDate();

							instance._endDate.setDate(endDate.getDate());
							instance._endDate.setMonth(endDate.getMonth());
							instance._endDate.setYear(endDate.getFullYear());
						},

						_setEndDatePickerDate: function() {
							var instance = this;

							instance._endDatePicker.clearSelection(true);

							instance._endDatePicker.selectDates([instance._endDate]);
						},

						_setEndTime: function() {
							var instance = this;

							var endTime = instance._endTimePicker.getTime();

							instance._endDate.setHours(endTime.getHours());
							instance._endDate.setMinutes(endTime.getMinutes());
						},

						_setEndTimePickerTime: function() {
							var instance = this;

							instance._endTimePicker.selectDates([instance._endDate]);
						},

						_setStartDate: function() {
							var instance = this;

							var startDate = instance._startDatePicker.getDate();

							instance._startDate.setDate(startDate.getDate());
							instance._startDate.setMonth(startDate.getMonth());
							instance._startDate.setYear(startDate.getFullYear());
						},

						_setStartDatePickerDate: function() {
							var instance = this;

							instance._startDatePicker.clearSelection(true);

							instance._startDatePicker.selectDates([instance._startDate]);
						},

						_setStartTime: function() {
							var instance = this;

							var startTime = instance._startTimePicker.getTime();

							instance._startDate.setHours(startTime.getHours());
							instance._startDate.setMinutes(startTime.getMinutes());
						},

						_setStartTimePickerTime: function() {
							var instance = this;

							instance._startTimePicker.selectDates([instance._startDate]);
						},

						_validate: function() {
							var instance = this;

							instance._validDate = (instance._duration > 0);

							var validDate = instance._validDate;

							var meetingEventDate = instance._containerNode;

							if (meetingEventDate) {
								meetingEventDate.toggleClass('error', !validDate);

								var helpInline = meetingEventDate.one('.help-inline');

								if (validDate && helpInline) {
									helpInline.remove();
								}

								if (!validDate && !helpInline) {
									var inlineHelp = A.Node.create('<div class="help-inline">' + Liferay.Language.get('the-end-time-must-be-after-the-start-time') + '</div>');

									meetingEventDate.insert(inlineHelp);
								}

								var submitButton = instance._submitButtonNode;

								if (submitButton) {
									submitButton.attr('disabled', !validDate);
								}
							}
						}
					}
				}
			);

			Liferay.MeetingScheduler = MeetingScheduler;
		},
		'',
		{
			requires: ['aui-base', 'liferay-portlet-base']
		}
	);

	AUI.add(
		'liferay-plugin-meeting-util',
		function(A) {
			var AArray = A.Array;
			var Lang = A.Lang;

			var isArray = Lang.isArray;

			var PARTICIPANT_TYPE_ATTENDEE = 0;

			var PARTICIPANT_TYPE_HOST = 1;

			var STR_BLANK = '';

			var STR_GUEST = 'guest';

			var STR_HASH = '#';

			var STR_KEY = 'key';

			var STR_NAME = 'name';

			var STR_PARTICIPANTS = 'participants';

			var STR_PARTICIPANT_MENU = 'participantMenu';

			var STR_PHRASE_MATCH = 'phraseMatch';

			var STR_SELECT = 'select';

			var TPL_AUTOCOMPLETE_RESULT = '<div class="avatar">' +
					'<img alt="{fullName}" src="{portraitURL}" />' +
				'</div>' +
				'<div class="user-info">' +
					'<span class="full-name">{fullName}</span>' +
					'<br />' +
					'<span class="email-address">{emailAddress}</span>' +
				'</div>';

			var TPL_PARTICIPANT_LIST_ITEM = '<div class="participant-invited" id="{namespace}powwowParticipantInvited{participantNumber}">' +
					'<span class="participant-name {cssClass}" title="{emailAddress}">{displayName}</span>' +
					'<a id="{namespace}participantMenu{participantNumber}" class="dropdown-toggle direction-down participant-menu" href="javascript:;">' +
						'<i class="caret"></i>' +
					'</a>' +
					'<ul class="dropdown-menu lfr-menu-list direction-down">' +
						'<li>' +
							'<a class="dropdown-item taglib-icon icon-ok make-host-participant" href="javascript:{namespace}makeHostParticipant({participantNumber})" > ' +
								Liferay.Language.get('make-host') +
							'</a>' +
						'</li>' +
						'<li>' +
							'<a class="dropdown-item taglib-icon icon-remove remove-participant" href="javascript:{namespace}removeParticipant({participantNumber})"> ' +
								Liferay.Language.get('delete') +
							'</a>' +
						'</li>' +
					'</ul>' +
				'</div>';

			var TPL_ROW_FIELDS_INPUTS = '<input id="{namespace}powwowParticipantName{participantNumber}" name="{namespace}powwowParticipantNames" type="hidden" value="{name}" />' +
				'<input id="{namespace}powwowParticipantParticipantUserId{participantNumber}" name="{namespace}powwowParticipantParticipantUserIds" type="hidden" value="{participantUserId}" />' +
				'<input class="participant-email-address" id="{namespace}powwowParticipantEmailAddress{participantNumber}" name="{namespace}powwowParticipantEmailAddresses" type="hidden" value="{emailAddress}" />' +
				'<input class="participant-type" id="{namespace}powwowParticipantType{participantNumber}" name="{namespace}powwowParticipantTypes" type="hidden" value="{type}" />';

			var MeetingUtil = A.Component.create(
				{
					ATTRS: {
						participants: {
							validator: isArray,
							value: []
						}
					},

					AUGMENTS: [Liferay.PortletBase],

					EXTENDS: A.Base,

					NAME: 'meetingutil',

					prototype: {
						initializer: function(config) {
							var instance = this;

							var formName = config.formName;
							var participantKeywords = config.participantKeywords;

							instance._baseActionURL = config.baseActionURL;
							instance._baseResourceURL = config.baseResourceURL;
							instance._creatorName = config.creatorName;
							instance._creatorEmailAddress = config.creatorEmailAddress;
							instance._creatorUserId = config.creatorUserId;
							instance._formName = formName;
							instance._meetingId = config.meetingId;
							instance._participantDataField = instance.byId(config.participantDataField);
							instance._participantInvitedSelector = config.participantInvitedSelector;
							instance._participantKeywords = participantKeywords;
							instance._participantListContainer = instance.byId(config.participantList);
							instance._participantNameInput = instance.byId(config.participantName);
							instance._portletKey = config.portletKey;
							instance._prefixParticipantEmailAddress = config.prefixParticipantEmailAddress;
							instance._prefixParticipantInvited = config.prefixParticipantInvited;
							instance._prefixParticipantName = config.prefixParticipantName;
							instance._prefixParticipantParticipantUserId = config.prefixParticipantParticipantUserId;
							instance._prefixParticipantType = config.prefixParticipantType;
							instance._redirect = config.redirect;
							instance._rowFieldsSelector = config.rowFieldsSelector;

							instance._form = instance.byId(formName);
							instance._participantKeywordsInput = instance.byId(participantKeywords);

							instance._enableEmailInput = false;

							instance._bindAutoComplete();

							instance._bindUI();

							if (instance._meetingId <= 0) {
								instance.addParticipantToList(instance._creatorName, instance._creatorUserId, instance._creatorEmailAddress, 'user', false, PARTICIPANT_TYPE_HOST);

								instance._currentHostEmailAddress = instance._creatorEmailAddress;
								instance._currentHostParticipantUserId = instance._creatorUserId;
							}
						},

						addParticipantToList: function(name, participantUserId, emailAddress, cssClass, existing, type) {
							var instance = this;

							var namespace = instance.NS;
							var participantListContainer = instance._participantListContainer;

							var participants = instance.get(STR_PARTICIPANTS);

							var participantIndex = instance._findParticipant(participantUserId, emailAddress);

							var participant = {};

							if (participantIndex >= 0) {
								participant = participants[participantIndex];

								if (!participant.removed) {
									return;
								}

								participant.removed = false;

								participants[participantIndex] = participant;
							}
							else {
								participant.participantNumber = participants.length;
								participant.name = name;
								participant.emailAddress = emailAddress;
								participant.existing = existing;
								participant.participantUserId = participantUserId;
								participant.removed = false;
								participant.type = type;

								participants.push(participant);
							}

							instance.set(STR_PARTICIPANTS, participants);

							var displayName = instance._getParticipantDisplayName(participant.name, participant.participantUserId, participant.emailAddress, participant.type);

							var data = {
								cssClass: cssClass,
								displayName: displayName,
								emailAddress: participant.emailAddress,
								name: participant.name,
								namespace: namespace,
								participantNumber: participant.participantNumber,
								participantUserId: participant.participantUserId,
								type: participant.type
							};

							if (participantListContainer) {
								participantListContainer.append(Lang.sub(TPL_PARTICIPANT_LIST_ITEM, data));

								var makeHostSelector = instance.one(STR_HASH + namespace + instance._prefixParticipantInvited + participant.participantNumber + ' .make-host-participant');

								if ((cssClass == STR_GUEST) || (instance._meetingId > 0)) {
									makeHostSelector.hide();
								}

								var participantMenu = instance.byId(STR_PARTICIPANT_MENU + participant.participantNumber);

								if (participant.type == PARTICIPANT_TYPE_HOST) {
									participantMenu.hide();
								}
							}

							Liferay.Menu.register(namespace + STR_PARTICIPANT_MENU + participant.participantNumber);

							var rowFields = instance.one(instance._rowFieldsSelector);

							if (rowFields) {
								rowFields.append(Lang.sub(TPL_ROW_FIELDS_INPUTS, data));
							}
						},

						editParticipantFromList: function(participantData) {
							var instance = this;

							var participants = instance.get(STR_PARTICIPANTS);
							var participantNumber = participantData.participantNumber;

							var participant = participants[participantNumber];

							var type = participantData.type;

							if (!participant) {
								return;
							}

							if (typeof type !== 'undefined') {
								participants[participantNumber].type = type;

								var powwowParticipantType = instance.byId(instance._prefixParticipantType + participantNumber);

								if (powwowParticipantType) {
									powwowParticipantType.val(type);
								}

								var powwowParticipantInvitedName = instance.one(STR_HASH + instance.NS + instance._prefixParticipantInvited + participantNumber + ' .participant-name');

								if (powwowParticipantInvitedName) {
									var displayName = instance._getParticipantDisplayName(participant.name, participant.participantUserId, participant.emailAddress, type);

									powwowParticipantInvitedName.set('text', displayName);
								}

								var participantMenu = instance.byId(STR_PARTICIPANT_MENU + participant.participantNumber);

								if (type == PARTICIPANT_TYPE_HOST) {
									participantMenu.hide();
								}
								else {
									participantMenu.show();
								}
							}

							instance.set(STR_PARTICIPANTS, participants);
						},

						removeParticipantFromList: function(participantNumber) {
							var instance = this;

							var participants = instance.get(STR_PARTICIPANTS);

							var participant = participants[participantNumber];

							if (participant) {
								if (participant.removed) {
									return;
								}

								participants[participantNumber].removed = true;
							}

							instance.set(STR_PARTICIPANTS, participants);

							var powwowParticipantInvited = instance.byId(instance._prefixParticipantInvited + participantNumber);

							if (powwowParticipantInvited) {
								powwowParticipantInvited.remove();
							}

							var powwowParticipantName = instance.byId(instance._prefixParticipantName + participantNumber);

							if (powwowParticipantName) {
								powwowParticipantName.remove();
							}

							var powwowParticipantParticipantUserId = instance.byId(instance._prefixParticipantParticipantUserId + participantNumber);

							if (powwowParticipantParticipantUserId) {
								powwowParticipantParticipantUserId.remove();
							}

							var powwowParticipantEmailAddress = instance.byId(instance._prefixParticipantEmailAddress + participantNumber);

							if (powwowParticipantEmailAddress) {
								powwowParticipantEmailAddress.remove();
							}

							var powwowParticipantType = instance.byId(instance._prefixParticipantType + participantNumber);

							if (powwowParticipantType) {
								powwowParticipantType.remove();
							}
						},

						saveMeeting: function() {
							var instance = this;

							if (instance.byId(STR_NAME).val() == STR_BLANK) {
								return;
							}

							instance._sendMeetingForm();
						},

						_addParticipantByEmail: function(event) {
							var instance = this;

							if (instance._enableEmailInput) {
								event.preventDefault();

								instance._validateParticipantEmail.validateField(instance.NS + instance._participantKeywords);

								var participantKeywordsInput = instance._participantKeywordsInput;
								var participantNameInput = instance._participantNameInput;

								if (participantKeywordsInput && participantNameInput && !instance._validateParticipantEmail.hasErrors()) {
									instance.addParticipantToList(participantNameInput.val(), 0, participantKeywordsInput.val(), STR_GUEST, false, PARTICIPANT_TYPE_ATTENDEE);

									participantKeywordsInput.val(STR_BLANK);
									participantKeywordsInput.focus();

									participantNameInput.hide();
									participantNameInput.val(STR_BLANK);
								}
							}
						},

						_bindAutoComplete: function() {
							var instance = this;

							var participantKeywordsInput = instance._participantKeywordsInput;

							var inputNode = STR_HASH + instance.NS + instance._participantKeywords;
							var resultFilters = [STR_PHRASE_MATCH, instance._getAutoCompleteParticipantsResultFilter];

							var autoCompleteConfiguration = instance._getAutoCompleteConfiguration(inputNode, resultFilters, instance._getAutoCompleteParticipantsResultHandler);

							var participantsAutoCompleteList = new A.AutoCompleteList(autoCompleteConfiguration);

							participantsAutoCompleteList.after(
								STR_SELECT,
								function(itemNode, result) {
									if (participantKeywordsInput) {
										participantKeywordsInput.val(STR_BLANK);

										var validateParticipantEmail = instance._validateParticipantEmail;

										if (validateParticipantEmail.hasErrors()) {
											validateParticipantEmail.resetField(participantKeywordsInput);
										}
									}
								}
							);

							participantsAutoCompleteList.on(
								STR_SELECT,
								function(itemNode, result) {
									instance.addParticipantToList(itemNode.result.raw.fullName, itemNode.result.raw.userId, itemNode.result.raw.emailAddress, 'user', false, PARTICIPANT_TYPE_ATTENDEE);
								}
							);
						},

						_bindUI: function() {
							var instance = this;

							var form = instance._form;
							var namespace = instance.NS;
							var participantKeywords = instance._participantKeywords;
							var participantKeywordsInput = instance._participantKeywordsInput;
							var participantNameInput = instance._participantNameInput;

							var rules = {};

							rules[namespace + participantKeywords] = {};
							rules[namespace + participantKeywords].email = true;

							instance._validateParticipantEmail = new A.FormValidator(
								{
									boundingBox: STR_HASH + namespace + instance._formName,
									rules: rules
								}
							);

							if (participantKeywordsInput) {
								participantKeywordsInput.on(
									STR_KEY,
									function(event) {
										instance._addParticipantByEmail(event);
									},
									'enter'
								);

								participantKeywordsInput.on(
									'keyup',
									function(event) {
										if ((participantKeywordsInput.val() == STR_BLANK) && instance._validateParticipantEmail.hasErrors()) {
											instance._validateParticipantEmail.resetField(participantKeywordsInput);
										}
									}
								);
							}

							if (participantNameInput) {
								participantNameInput.on(
									STR_KEY,
									function(event) {
										instance._addParticipantByEmail(event);
									},
									'enter,tab'
								);
							}

							if (form) {
								form.on(
									'submit',
									function(event) {
										instance.saveMeeting();
									}
								);
							}

							Liferay.provide(
								window,
								instance.NS + 'removeParticipant',
								function(participantNumber) {
									instance.removeParticipantFromList(participantNumber);
								},
								['aui-base']
							);

							Liferay.provide(
								window,
								instance.NS + 'makeHostParticipant',
								function(participantNumber) {
									var participantData = {};

									participantData.type = PARTICIPANT_TYPE_ATTENDEE;
									participantData.participantNumber = instance._findParticipant(instance._currentHostParticipantUserId, instance._currentHostEmailAddress);

									instance.editParticipantFromList(participantData);

									var hostData = {};

									hostData.type = PARTICIPANT_TYPE_HOST;
									hostData.participantNumber = participantNumber;

									instance.editParticipantFromList(hostData);
								},
								['aui-base']
							);
						},

						_findParticipant: function(participantUserId, emailAddress) {
							var instance = this;

							var participants = instance.get(STR_PARTICIPANTS);

							for (var i = 0; i < participants.length; i++) {
								var participant = participants[i];

								if (participantUserId > 0) {
									if (participant.participantUserId == participantUserId) {
										return i;
									}
								}
								else {
									if (participant.emailAddress == emailAddress) {
										return i;
									}
								}
							}

							return -1;
						},

						_getAutoCompleteConfiguration: function(inputNode, resultFilters, resultHandler) {
							var instance = this;

							var configuration = {};

							configuration.activateFirstItem = 'true';
							configuration.inputNode = inputNode;
							configuration.render = 'true';

							if (resultFilters) {
								configuration.resultFilters = resultFilters;
							}

							configuration.resultFormatter = instance._getAutoCompleteResultFormatter;
							configuration.resultHighlighter = STR_PHRASE_MATCH;
							configuration.resultTextLocator = instance._getAutoCompleteResultTextLocator;

							configuration.source = function(query, callback) {
								instance._getAutoCompleteSource(query, callback, instance, resultHandler);
							};

							return configuration;
						},

						_getAutoCompleteParticipantsResultFilter: function(query, results) {
							var instance = this;

							var newResults = [];

							for (var i = 0; i < results.length; i++) {
								if (!A.one('.participant-email-address[value="' + results[i].raw.emailAddress + '"]')) {
									newResults.push(results[i]);
								}
							}

							return newResults;
						},

						_getAutoCompleteParticipantsResultHandler: function(responseData, instance) {
							var participantNameInput = instance._participantNameInput;

							var noResults = (responseData.length == 0);

							instance._enableEmailInput = noResults;

							if (participantNameInput) {
								participantNameInput.toggle((instance._participantKeywordsInput.val() != STR_BLANK) && noResults);
							}
						},

						_getAutoCompleteResultFormatter: function(query, results) {
							return AArray.map(
								results,
								function(result) {
									return Lang.sub(TPL_AUTOCOMPLETE_RESULT, result.raw);
								}
							);
						},

						_getAutoCompleteResultTextLocator: function(result) {
							return result.fullName + ' <' + result.emailAddress + '>';
						},

						_getAutoCompleteSource: function(query, callback, instance, resultHandler) {
							var portletURL = new Liferay.PortletURL.createURL(instance._baseResourceURL);

							portletURL.setResourceId('getUsers');

							var data = {};

							data[instance.NS + STR_NAME] = query;

							A.io.request(
								portletURL.toString(),
								{
									autoLoad: false,
									data: data,
									dataType: 'JSON',
									method: 'POST',
									on: {
										success: function() {
											var responseData = this.get('responseData');

											resultHandler(responseData, instance);

											callback(responseData);
										}
									},
									sync: false
								}
							).start();
						},

						_getParticipantDisplayName: function(name, participantUserId, emailAddress, type) {
							var instance = this;

							var displayName = name || emailAddress;

							if (type == PARTICIPANT_TYPE_HOST) {
								displayName = Lang.sub(Liferay.Language.get('x-host'), [displayName]);

								instance._currentHostEmailAddress = emailAddress;
								instance._currentHostParticipantUserId = participantUserId;
							}

							return displayName;
						},

						_sendMeetingForm: function() {
							var instance = this;

							instance._form.target = STR_BLANK;

							var portletURL = new Liferay.PortletURL.createURL(instance._baseActionURL);

							portletURL.setParameter('javax.portlet.action', 'updatePowwowMeeting');
							portletURL.setPortletId(instance._portletKey);
							portletURL.setWindowState('normal');

							var participantDataField = instance._participantDataField;

							participantDataField.val(JSON.stringify(instance.get(STR_PARTICIPANTS)));

							A.io.request(
								portletURL.toString(),
								{
									form: {
										id: instance._form
									},
									on: {
										success: function(event, id, obj) {
											window.location.href = instance._redirect;
										}
									}
								}
							);
						}
					}
				}
			);

			Liferay.MeetingUtil = MeetingUtil;
		},
		'',
		{
			requires: ['aui-base', 'liferay-menu', 'liferay-portlet-base', 'liferay-portlet-url']
		}
	);
}());