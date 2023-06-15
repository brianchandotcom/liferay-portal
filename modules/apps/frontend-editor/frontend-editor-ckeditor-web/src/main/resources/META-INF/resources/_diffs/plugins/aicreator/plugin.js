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

(function () {
	const pluginName = 'aicreator';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			const plugin = this;

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

					Liferay.Util.openModal({
						height: '550px',
						onClose: () => closeModalHandler.detach(),
						size: 'lg',
						title: Liferay.Language.get('ai-creator'),
						url: editor.config.aiCreatorOpenAIURL,
					});
				},
			});

			editor.addCommand('openAICreatorConfigurationPopover', {
				exec: () => {
					const button = editor.container.findOne(
						'.cke_button__aicreator'
					).$;
					const popover = document.createElement('div');

					popover.className = 'clay-popover-top fade popover show';
					popover.innerHTML = POPOVER_CONTENT_TEMPLATE;
					popover.setAttribute('role', 'alert');
					popover.setAttribute('tabindex', '0');

					const removePopover = () => {
						popover.removeEventListener('blur', removePopover);

						if (document.body.contains(popover)) {
							document.body.removeChild(popover);

							if (document.body.contains(button)) {
								button.focus();
							}
						}
					};

					document.body.appendChild(popover);

					requestAnimationFrame(() => {
						popover.focus();
						popover.addEventListener('blur', removePopover);

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
