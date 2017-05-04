AUI.add(
	'liferay-ddl-form-builder-copy-publish-form-url-popover',
	function(A) {
		var FormBuilderCopyPublishFormURLPopover = A.Component.create(
			{
				ATTRS: {
					portletNamespace: {
						value: ''
					},

					publishURL: {
						setter: '_setPublishURL',
						value: ''
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-form-builder-copy-publish-form-url-popover',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._popover = instance._createPopover();
					},

					destructor: function() {
						var instance = this;

						instance._popover.destroy();
					},

					hide: function() {
						var instance = this;

						instance._popover.hide();
					},

					isVisible: function() {
						var instance = this;

						return instance._popover.get('visible');
					},

					setAlign: function(value) {
						var instance = this;

						instance._popover.set("align", value);
					},

					show: function() {
						var instance = this;

						instance._popover.show();
					},

					_createPopover: function() {
						var instance = this;

						var popover = new A.Popover(
							{
								bodyContent: A.one('.publish-popover-content'),
								constrain: false,
								cssClass: 'form-builder-publish-popover',
								position: 'left',
								visible: false,
								width: 500,
								zIndex: 999
							}
						).render();

						popover.set(
							'hideOn',[
								{
									eventName: 'key',
									keyCode: 'esc',
									node: A.getDoc()
								},
								{
									eventName: 'clickoutside',
									node: A.one('.form-builder-publish-popover')
								}
							]
						);

						popover.after("visibleChange", function(event) {

							if (event.prevVal) {
								var popoverContent = A.one('.publish-popover-content');

								var formGroup = popoverContent.one('.form-group');

								formGroup.removeClass('has-error');
								formGroup.removeClass('has-success');

								var copyButton = popoverContent.one('.btn');

								copyButton.removeClass('btn-danger');
								copyButton.removeClass('btn-success');

								popoverContent.one('.publish-button-text').html(Liferay.Language.get('copy'));
							}
						});

						return popover;
					},

					_setPublishURL: function(publishFormURL) {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var clipboardInputSelector = '#' + portletNamespace + 'clipboard';

						var clipboardInput = A.one(clipboardInputSelector);

						clipboardInput.set('value', publishFormURL);
					}
				}

			}
		);

		Liferay.namespace('DDL').FormBuilderCopyPublishFormURLPopover = FormBuilderCopyPublishFormURLPopover;
	},
	'',
	{
		requires: ['aui-popover', 'event-outside']
	}
);