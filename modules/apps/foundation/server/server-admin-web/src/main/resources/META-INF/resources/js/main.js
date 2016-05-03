AUI.add(
	'liferay-admin',
	function(A) {
		var Lang = A.Lang;

		var INTERVAL_RENDER_IDLE = 60000;

		var INTERVAL_RENDER_IN_PROGRESS = 2000;

		var MAP_DATA_PARAMS = {
			classname: 'className'
		};

		var STR_CLICK = 'click';

		var STR_FORM = 'form';

		var STR_INDEX_ACTIONS_PANEL = 'indexActionsPanel';

		var STR_URL = 'url';

		var Admin = A.Component.create(
			{
				ATTRS: {
					form: {
						setter: A.one,
						value: null
					},

					indexActionsPanel: {
						value: null
					},

					redirectUrl: {
						validator: Lang.isString,
						value: null
					},

					submitButton: {
						validator: Lang.isString,
						value: null
					},

					url: {
						value: null
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'admin',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._eventHandles = [];

						instance.bindUI();

						instance._laterTimeout = A.later(INTERVAL_RENDER_IN_PROGRESS, instance, '_updateIndexActions');
					},

					bindUI: function() {
						var instance = this;

						instance._eventHandles.push(
							instance.get(STR_FORM).delegate(
								STR_CLICK,
								A.bind('_onSubmit', instance),
								instance.get('submitButton')
							)
						);
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._eventHandles = null;

						A.clearTimeout(instance._laterTimeout);
					},

					_addInputsFromData: function(data) {
						var instance = this;

						var form = instance.get(STR_FORM);

						var inputsArray = A.Object.map(
							data,
							function(value, key) {
								key = MAP_DATA_PARAMS[key] || key;

								var nsKey = instance.ns(key);

								return '<input id="' + nsKey + '" name="' + nsKey + '" type="hidden" value="' + value + '" />';
							}
						);

						form.append(inputsArray.join(''));
					},

					_installXuggler: function(event) {
						var instance = this;

						var form = instance.get(STR_FORM);

						var data = A.IO.stringify(form.getDOM());

						data = A.QueryString.parse(data);

						var redirectKey = instance.ns('redirect');

						var url = Liferay.Util.addParams('p_p_isolated=1', instance.get(STR_URL));

						data[redirectKey] = Liferay.Util.addParams('p_p_isolated=1', data[redirectKey]);

						A.one('#adminXugglerPanelContent').load(
							url,
							{
								data: data,
								loadingMask: {
									'strings.loading': Liferay.Language.get('xuggler-library-is-installing')
								},
								selector: '#adminXugglerPanelContent',
								where: 'outer'
							}
						);
					},

					_isBackgroundTaskInProgress: function() {
						var instance = this;

						var indexActionsNode = A.one(instance.get(STR_INDEX_ACTIONS_PANEL));

						return !!(indexActionsNode && indexActionsNode.one('.background-task-status-in-progress'));
					},

					_onSubmit: function(event) {
						var instance = this;

						var data = event.currentTarget.getData();
						var form = instance.get(STR_FORM);

						var cmd = data.cmd;
						var redirect = instance.one('#redirect', form);

						if (redirect) {
							redirect.val(instance.get('redirectURL'));
						}

						instance._addInputsFromData(data);

						if (cmd === 'installXuggler') {
							var cmdNode = instance.one('#cmd');

							instance._installXuggler();

							if (cmdNode) {
								cmdNode.remove();
							}

							instance._installXuggler();
						}
						else {
							submitForm(
								form,
								instance.get(STR_URL)
							);
						}
					},

					_updateIndexActions: function() {
						var instance = this;

						var renderInterval = INTERVAL_RENDER_IDLE;

						if (instance._isBackgroundTaskInProgress()) {
							renderInterval = INTERVAL_RENDER_IN_PROGRESS;
						}

						var currentAdminIndexPanel = A.one(instance.get(STR_INDEX_ACTIONS_PANEL));

						if (currentAdminIndexPanel) {
							A.io.request(
								instance.get(STR_URL),
								{
									on: {
										success: function(event, id, obj) {
											var responseDataNode = A.Node.create(this.get('responseData'));

											var responseAdminIndexPanel = responseDataNode.one(instance.get(STR_INDEX_ACTIONS_PANEL));

											var responseAdminIndexNodeList = responseAdminIndexPanel.all('.index-action-wrapper');

											var currentAdminIndexNodeList = currentAdminIndexPanel.all('.index-action-wrapper');

											currentAdminIndexNodeList.each(
												function(item, index) {
													var inProgress = item.one('.progress');

													var responseAdminIndexNode = responseAdminIndexNodeList.item(index);

													if (!inProgress) {
														inProgress = responseAdminIndexNode.one('.progress');
													}

													if (inProgress) {
														item.replace(responseAdminIndexNode);
													}
												}
											);
										}
									}
								}
							);
						}

						instance._laterTimeout = A.later(renderInterval, instance, '_updateIndexActions');
					}
				}
			}
		);

		Liferay.Portlet.Admin = Admin;
	},
	'',
	{
		requires: ['aui-io-plugin-deprecated', 'aui-io-request', 'liferay-portlet-base', 'querystring-parse']
	}
);