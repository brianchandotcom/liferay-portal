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

import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

const KEY_LABEL = Liferay.Browser.isMac() ? '⌘' : 'Ctrl';

export default function ShortcutModal({onCloseModal}) {
	const {observer} = useModal({onClose: () => onCloseModal()});

	return (
		<ClayModal
			containerProps={{className: 'cadmin'}}
			observer={observer}
			size="md"
		>
			<ClayModal.Header>
				{Liferay.Language.get('keyboard-shortcuts')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="sheet-subtitle text-secondary">
					{Liferay.Language.get('fragments')}
				</p>

				<KeyboardShorcut
					description={Liferay.Language.get('move-fragment-up')}
					keyCombinations={['↑']}
				/>

				<KeyboardShorcut
					description={Liferay.Language.get('move-fragment-down')}
					keyCombinations={['↓']}
				/>

				<KeyboardShorcut
					description={Liferay.Language.get('duplicate-fragment')}
					keyCombinations={[KEY_LABEL, 'D']}
				/>

				<KeyboardShorcut
					description={Liferay.Language.get('delete-fragment')}
					keyCombinations={['⌫']}
				/>

				<KeyboardShorcut
					description={Liferay.Language.get(
						'save-composition-for-containers-and-grids'
					)}
					keyCombinations={[KEY_LABEL, 'S']}
				/>

				<p className="sheet-subtitle text-secondary">
					{Liferay.Language.get('view')}
				</p>

				<KeyboardShorcut
					description={Liferay.Language.get('hide-sidebars')}
					keyCombinations={[KEY_LABEL, '⇧', '.']}
				/>

				<KeyboardShorcut
					description={Liferay.Language.get('show-sidebars')}
					keyCombinations={[KEY_LABEL, '⇧', '.']}
				/>
			</ClayModal.Body>
		</ClayModal>
	);
}

function KeyboardShorcut({description, keyCombinations}) {
	return (
		<div className="align-items-center d-flex mb-3">
			<div className="page-editor__shorcut-modal__shorcut text-right">
				<kbd className="c-kbd text-secondary">
					{keyCombinations.map((key, index) => (
						<>
							<kbd className="c-kbd">{key}</kbd>

							{index < keyCombinations.length - 1 ? (
								<span className="c-kbd-separator">+</span>
							) : null}
						</>
					))}
				</kbd>
			</div>

			<p className="mb-0 ml-3 page-editor__shorcut-modal__shorcut-description text-3 text-weight-semi-bold">
				{description}
			</p>
		</div>
	);
}
