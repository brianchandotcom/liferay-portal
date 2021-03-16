/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayForm from '@clayui/form';
import getCN from 'classnames';
import React, {useState} from 'react';

import CodeMirrorEditor from '../CodeMirrorEditor';

function JSONInput({
	configKey,
	disabled,
	initialValue,
	label = Liferay.Language.get('json'),
	onChange,
}) {
	const [value, setValue] = useState(
		JSON.stringify(initialValue, null, '\t')
	);
	const [hasError, setHasError] = useState(false);

	const _handleBlur = () => {
		try {
			onChange(configKey, JSON.parse(value));
		}
		catch {}
	};

	function _handleChange(value) {
		setValue(value);

		try {
			JSON.parse(value);

			setHasError(false);
		}
		catch {
			setHasError(true);
		}
	}

	return (
		<div
			className={getCN(
				'custom-json',
				{disabled},
				{'has-error': hasError}
			)}
			onBlur={_handleBlur}
		>
			<label>{label}</label>

			<CodeMirrorEditor onChange={_handleChange} value={value} />

			{hasError && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />
						{Liferay.Language.get(
							'unable-to-apply-changes-due-to-invalid-json'
						)}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</div>
	);
}

export default JSONInput;
