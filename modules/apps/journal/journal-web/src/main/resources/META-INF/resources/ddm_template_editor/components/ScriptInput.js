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

import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

const SCRIPT_INPUT_ID = 'ddm_template_editor__ScriptInput';

export const ScriptInput = ({onSelectScript}) => {
	const handleChange = (event) => {
		const target = event.target;
		const [file] = target.files || [];

		if (file) {
			file.text()
				.then((text) => {
					if (text) {
						onSelectScript(text);

						openToast({
							message: Liferay.Util.sub(
								Liferay.Language.get('x-imported'),
								file.name
							),
							title: Liferay.Language.get('success'),
							type: 'success',
						});
					}
					else {
						showInvalidFileError();
					}
				})
				.catch(() => {
					showInvalidFileError();
				})
				.finally(() => {
					target.value = '';
				});
		}
		else {
			target.value = '';

			showInvalidFileError();
		}
	};

	return (
		<div className="form-group input-text-wrapper mt-4">
			<label className="control-label" htmlFor={SCRIPT_INPUT_ID}>
				Script File
			</label>

			<input
				className="field form-control"
				id={SCRIPT_INPUT_ID}
				onChange={handleChange}
				type="file"
			/>
		</div>
	);
};

ScriptInput.propTypes = {
	onSelectScript: PropTypes.func.isRequired,
};

function showInvalidFileError() {
	openToast({
		message: Liferay.Language.get(
			'an-unexpected-error-occurred-while-importing-the-script'
		),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
}
