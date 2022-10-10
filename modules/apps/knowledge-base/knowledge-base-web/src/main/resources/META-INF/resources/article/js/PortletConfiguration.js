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

export default function _PortletConfiguration({
	eventName,
	namespace,
	selectKBObjectURL,
}) {
	const form = document.getElementById(`${namespace}fm`);

	if (form) {
		document
			.getElementById(`${namespace}selectKBArticleButton`)
			.addEventListener('click', () => {
				Liferay.Util.openSelectionModal({
					onSelect: (event) => {
						Liferay.Util.selectFolder(
							{
								idString: 'resourcePrimKey',
								idValue: event.resourceprimkey,
								nameString: 'configurationKBObject',
								nameValue: event.title,
							},
							namespace
						);
					},
					selectEventName: eventName,
					title: Liferay.Language.get('select-article'),
					url: selectKBObjectURL,
				});
			});
	}
}
