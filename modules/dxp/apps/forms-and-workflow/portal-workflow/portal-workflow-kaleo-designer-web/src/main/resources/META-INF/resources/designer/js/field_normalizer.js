AUI.add(
	'liferay-kaleo-designer-field-normalizer',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var KaleoDesignerRemoteServices = Liferay.KaleoDesignerRemoteServices;

		var isObject = Lang.isObject;
		var isValue = Lang.isValue;

		var COL_TYPES_ASSIGNMENT = ['address', 'receptionType', 'resourceActions', 'roleId', 'roleType', 'scriptedAssignment', 'scriptedRecipient', 'taskAssignees', 'user', 'userId'];

		var STR_BLANK = '';

		var populateRole = function(assignments) {
			KaleoDesignerRemoteServices.getRole(
				assignments.roleId,
				function(data) {
					AArray.each(
						data,
						function(item) {
							if (item) {
								var index = assignments.roleId.indexOf(item.roleId);

								assignments.roleNameAC[index] = item.name;
							}
						}
					);
				}
			);
		};

		var populateUser = function(assignments) {
			if (isValue(assignments.userId)) {
				KaleoDesignerRemoteServices.getUser(
					assignments.userId,
					function(data) {
						AArray.each(
							data,
							function(item) {
								if (item) {
									var index = assignments.userId.indexOf(item.userId);

									assignments.emailAddress[index] = item.emailAddress;
									assignments.fullName[index] = item.fullName;
									assignments.screenName[index] = item.screenName;
								}
							}
						);
					}
				);
			}
		};

		var _put = function(obj, key, value) {
			obj[key] = obj[key] || [];

			obj[key].push(value);
		};

		var FieldNormalizer = {
			normalizeToActions: function(data) {
				var actions = {};

				data = data || {};

				data.forEach(
					function(item1, index1, collection1) {
						A.each(
							item1,
							function(item2, index2, collection2) {
								if (isValue(item2)) {
									if (index2 === 'script') {
										item2 = Lang.trim(item2);
									}

									_put(actions, index2, item2);
								}
							}
						);
					}
				);

				return actions;
			},

			normalizeToAssignments: function(data) {
				var assignments = {
					assignmentType: [STR_BLANK]
				};

				if (data && data.length) {
					COL_TYPES_ASSIGNMENT.forEach(
						function(item1, index1, collection1) {
							var value = data[0][item1];

							if (!isValue(value)) {
								return;
							}

							var assignmentValue = AArray(value);

							assignmentValue.forEach(
								function(item2, index2, collection2) {
									if (isObject(item2)) {
										A.each(
											item2,
											function(item3, index3, collection3) {
												_put(assignments, index3, item3);
											}
										);
									}
									else {
										_put(assignments, item1, item2);
									}
								}
							);

							if (isValue(value) && AArray.some(value, isValue)) {
								assignments.assignmentType = AArray(item1);
							}
						}
					);

					if (assignments.assignmentType == 'roleId') {
						populateRole(assignments);
					}
					else if (assignments.assignmentType == 'user') {
						populateUser(assignments);
					}
				}

				return assignments;
			},

			normalizeToDelays: function(data) {
				var delays = {};

				data = data || {};

				data.forEach(
					function(item1, index1, collection1) {
						A.each(
							item1,
							function(item2, index2, collection2) {
								if (isValue(item2)) {
									_put(delays, index2, item2);
								}
							}
						);
					}
				);

				return delays;
			},

			normalizeToNotifications: function(data) {
				var notifications = {};

				data = data || {};

				data.forEach(
					function(item1, index1, collection1) {
						A.each(
							item1,
							function(item2, index2, collection2) {
								if (isValue(item2)) {
									if (index2 === 'recipients') {
										if (item2[0] && item2[0].receptionType) {
											_put(notifications, 'receptionType', item2[0].receptionType);
										}

										item2 = FieldNormalizer.normalizeToAssignments(item2);
									}

									_put(notifications, index2, item2);
								}
							}
						);
					}
				);

				return notifications;
			},

			normalizeToTaskTimers: function(data) {
				var taskTimers = {};

				data = data || {};

				data.forEach(
					function(item1, index1, collection1) {
						A.each(
							item1,
							function(item2, index2, collection2) {
								if (isValue(item2)) {
									if (index2 === 'delay' || index2 === 'recurrence') {
										return;
									}
									else if (index2 === 'timerNotifications') {
										item2 = FieldNormalizer.normalizeToNotifications(item2);
									}
									else if (index2 === 'timerActions') {
										item2 = FieldNormalizer.normalizeToActions(item2);
									}
									else if (index2 === 'reassignments') {
										item2 = FieldNormalizer.normalizeToAssignments(item2);
									}

									_put(taskTimers, index2, item2);
								}
							}
						);

						var delays = item1.delay.concat(item1.recurrence);

						_put(taskTimers, 'delay', FieldNormalizer.normalizeToDelays(delays));
					}
				);

				return taskTimers;
			}
		};

		Liferay.KaleoDesignerFieldNormalizer = FieldNormalizer;
	},
	'',
	{
		requires: ['liferay-kaleo-designer-remote-services']
	}
);