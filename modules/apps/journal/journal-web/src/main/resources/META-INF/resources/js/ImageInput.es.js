/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

export default function ImageInput({name, portletNamespace, previewURL}) {
	const [fileName, setFileName] = useState(previewURL || '');
	const imageTitleId = `${portletNamespace}${name}`;
	const inputRef = useRef();

	return (
		<div className="mb-3">
			{previewURL ? (
				<div className="aspect-ratio aspect-ratio-16-to-9 mb-2 rounded">
					<img
						alt={Liferay.Language.get('preview')}
						className="aspect-ratio-item-fluid"
						src={previewURL}
					/>
				</div>
			) : null}

			{name ? (
				<ClayForm.Group>
					<label className="sr-only" htmlFor={imageTitleId}>
						{Liferay.Language.get('image')}
					</label>

					<ClayInput.Group small>
						<ClayInput.GroupItem>
							<input
								className="sr-only"
								id={imageTitleId}
								name={name}
								onChange={(event) =>
									setFileName(
										event.target.files?.[0]?.name || ''
									)
								}
								ref={inputRef}
								type="file"
							/>

							<ClayInput
								onClick={() => inputRef.current?.click()}
								placeholder={Liferay.Language.get(
									'select-image'
								)}
								readOnly
								sizing="sm"
								value={fileName}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem shrink>
							<ClayButton
								displayType="secondary"
								monospaced
								onClick={() => inputRef.current?.click()}
								size="sm"
								title={sub(
									fileName
										? Liferay.Language.get('change-x')
										: Liferay.Language.get('select-x'),
									Liferay.Language.get('image')
								)}
							>
								<ClayIcon
									className="mt-0"
									symbol={fileName ? 'change' : 'plus'}
								/>
							</ClayButton>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			) : null}
		</div>
	);
}
