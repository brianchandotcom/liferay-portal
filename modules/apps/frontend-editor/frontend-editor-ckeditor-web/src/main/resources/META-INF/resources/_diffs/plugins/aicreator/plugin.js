/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function () {
	const POPOVER_CONTENT_TEMPLATE = `
		<div class="arrow"></div>
		<div class="inline-scroller">
			<div class="popover-header">
				${Liferay.Language.get('configure-openai')}
			</div>
			<div class="popover-body">
				${Liferay.Language.get(
					'api-authentication-is-needed-to-use-this-feature.-add-an-api-key-from-the-settings-page-or-contact-your-administrator'
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

					if (button) {
						if (document.body.contains(button)) {
							button.focus();
						}

						button = null;
					}
				}
			}

			function showPopover() {
				hidePopover();

				button = editor.container.findOne('.cke_button__aicreator').$;
				popover = document.createElement('div');

				popover.className = 'clay-popover-top fade popover show';
				popover.innerHTML = POPOVER_CONTENT_TEMPLATE;
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
		},
	});
})();
