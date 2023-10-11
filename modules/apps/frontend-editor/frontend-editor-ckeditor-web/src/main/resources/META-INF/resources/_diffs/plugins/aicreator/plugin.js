/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const ON_CLICK_POPOVER_CONTENT = `
		<div class="arrow"></div>
		<div class="inline-scroller">
			<div class="popover-header">
				${Liferay.Language.get('configure-openai')}
			</div>
			<div class="popover-body">
				${Liferay.Language.get(
					'authentication-is-needed-to-use-this-feature.-contact-your-administrator-to-add-an-api-key-in-instance-or-site-settings'
				)}
			</div>
		</div>
	`;

	const pluginName = 'aicreator';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			const plugin = this;

			let button = null;
			let popover = null;

			function hidePopover() {
				if (popover) {
					if (document.body.contains(popover)) {
						document.body.removeChild(popover);
					}

					popover = null;

					if (button && document.body.contains(button)) {
						button.focus();
					}
				}
			}

			function showPopover() {
				hidePopover();

				popover = document.createElement('div');

				popover.className = 'clay-popover-top fade popover show';
				popover.innerHTML = ON_CLICK_POPOVER_CONTENT;
				popover.setAttribute('role', 'alert');
				popover.setAttribute('tabindex', '0');

				document.body.appendChild(popover);

				requestAnimationFrame(() => {
					const buttonRect = button.getBoundingClientRect();
					const popoverRect = popover.getBoundingClientRect();

					popover.style.bottom = 'initial';
					popover.style.right = 'initial';

					popover.style.top = `${
						buttonRect.top - popoverRect.height
					}px`;

					popover.style.left = `${Math.floor(
						buttonRect.left +
							buttonRect.width / 2 -
							popoverRect.width / 2
					)}px`;
				});
			}

			editor.addCommand('openAICreatorDialog', {
				exec: () => {
					const closeModalHandler = Liferay.on(
						'closeModal',
						(event) => {
							closeModalHandler.detach();

							if (event.text) {
								editor.insertText(event.text);
							}
						}
					);

					const url = new URL(editor.config.aiCreatorOpenAIURL);

					url.searchParams.set(
						`${editor.config.aiCreatorPortletNamespace}languageId`,
						editor.config.contentsLanguage
					);

					Liferay.Util.openModal({
						height: '550px',
						onClose: () => closeModalHandler.detach(),
						size: 'lg',
						title: Liferay.Language.get('ai-creator'),
						url: url.toString(),
					});
				},
			});

			editor.addCommand('openAICreatorConfigurationPopover', {
				exec: () => {
					showPopover();

					const removePopover = () => {
						popover.removeEventListener('blur', removePopover);
						hidePopover();
					};

					requestAnimationFrame(() => {
						popover.focus();
						popover.addEventListener('blur', removePopover);
					});
				},
			});

			editor.ui.addButton('AICreator', {
				command: editor.config.isAICreatorOpenAIAPIKey
					? 'openAICreatorDialog'
					: 'openAICreatorConfigurationPopover',
				icon: `${plugin.path}assets/ai_creator.png`,
				label: Liferay.Language.get('ai-creator'),
			});

			requestAnimationFrame(() => {
				button = editor.container.findOne('.cke_button__aicreator').$;

				button.classList.add('lfr-portal-tooltip');

				button.removeAttribute('aria-labelledby');

				button.setAttribute(
					'aria-label',
					Liferay.Language.get('create-ai-content')
				);

				button.setAttribute(
					'title',
					Liferay.Language.get('create-ai-content')
				);
			});
		},
	});
})();
