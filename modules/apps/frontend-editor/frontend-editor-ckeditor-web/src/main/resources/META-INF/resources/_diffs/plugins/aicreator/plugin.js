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
					});
				},
			});

			editor.ui.addButton('AICreator', {
				command: 'openAICreatorDialog',
				icon: `${plugin.path}assets/ai_creator.png`,
				label: Liferay.Language.get('ai-creator'),
			});
		},
	});
})();
