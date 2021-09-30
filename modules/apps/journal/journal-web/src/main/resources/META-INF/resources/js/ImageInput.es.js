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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
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
							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() => inputRef.current?.click()}
								small
								symbol={fileName ? 'change' : 'plus'}
								title={Liferay.Util.sub(
									fileName
										? Liferay.Language.get('change-x')
										: Liferay.Language.get('select-x'),
									Liferay.Language.get('image')
								)}
							/>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			) : null}
		</div>
	);
}
