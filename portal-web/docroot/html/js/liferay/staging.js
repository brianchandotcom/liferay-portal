AUI.add(
	'liferay-staging',
	function(A) {
		var Lang = A.Lang;

		var StagingBar = {
			init: function(config) {
				var instance = this;

				instance._namespace = config.namespace;

				instance._bindUI();

				Liferay.publish(
					{
						fireOnce: true
					}
				);

				Liferay.after(
					'initStagingBar',
					function(event) {
						A.getBody().addClass('staging-ready');
					}
				);

				Liferay.fire('initStagingBar', config);
			},

			_bindSelectBoxNav: function() {
				var selectBoxNav = 'select.select-box-nav';

				var stagingBarContainer = A.one('.staging-bar');

				if (stagingBarContainer) {
					stagingBarContainer.delegate(
						'change',
						function(event) {
							var currentTarget = event.currentTarget;

							window.location.href = currentTarget.get('value');
						},
						selectBoxNav
					);
				}
			},

			_bindStagingLink: function() {
				var instance = this;

				var initialized = false;

				var stagingLink = A.one('.staging-bar .active.staging-link');

				if (stagingLink) {
					stagingLink.on(
						'click',
						function(event) {
							var currentTarget = event.currentTarget;

							var dropdownMenu = currentTarget.one('.dropdown-menu');

							var target = event.target;

							if (target.ancestor('.dropdown-toggle') || target.hasClass('dropdown-toggle')) {
								currentTarget.toggleClass('open');
							}

							var menuOpen = currentTarget.hasClass('open');

							if (menuOpen && !initialized) {
								currentTarget.once(
									'clickoutside',
									function(event) {
										this.removeClass('open');

										initialized = false;
									}
								);

								initialized = true;
							}
							else if (!menuOpen) {
								currentTarget.detach('clickoutside');

								initialized = false;
							}
						}
					);
				}
			},

			_bindUI: function() {
				var instance = this;

				instance._bindSelectBoxNav();

				instance._bindStagingLink();
			}
		};

		Liferay.StagingBar = StagingBar;
	},
	'',
	{
		requires: ['aui-io-plugin-deprecated', 'liferay-util-window']
	}
);