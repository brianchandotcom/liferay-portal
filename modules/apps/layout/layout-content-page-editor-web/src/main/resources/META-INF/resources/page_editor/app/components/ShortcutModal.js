/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

const KEY_LABEL = Liferay.Browser?.isMac() ? '⌘' : 'Ctrl';
const OPTION_KEY_LABEL = Liferay.Browser?.isMac() ? '⌥' : 'Alt';

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
					keyCombinations={[OPTION_KEY_LABEL, '⇧', '↑']}
				/>

				<KeyboardShorcut
					description={Liferay.Language.get('move-fragment-down')}
					keyCombinations={[OPTION_KEY_LABEL, '⇧', '↓']}
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
					{Liferay.Language.get('selection')}
				</p>

				<KeyboardShorcut
					description={Liferay.Language.get('select-parent')}
					keyCombinations={['⇧', 'Enter']}
				/>

				<p className="sheet-subtitle text-secondary">
					{Liferay.Language.get('view')}
				</p>

				<KeyboardShorcut
					description={Liferay.Language.get('toggle-sidebars')}
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
						<React.Fragment key={index}>
							<kbd className="c-kbd">{key}</kbd>

							{index < keyCombinations.length - 1 ? (
								<span className="c-kbd-separator">+</span>
							) : null}
						</React.Fragment>
					))}
				</kbd>
			</div>

			<p className="mb-0 ml-3 page-editor__shorcut-modal__shorcut-description text-3 text-weight-semi-bold">
				{description}
			</p>
		</div>
	);
}
