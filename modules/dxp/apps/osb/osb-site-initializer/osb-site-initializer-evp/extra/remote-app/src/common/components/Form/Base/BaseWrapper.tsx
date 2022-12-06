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
import classNames from 'classnames';
import {ReactNode} from 'react';

import InputWarning from '../../Form/Base/BaseWarning';

type BaseWrapperProps = {
	children: ReactNode;
	description?: string;
	error?: string;
	id?: string;
	label?: string;
	required?: boolean;
};

const BaseWrapper: React.FC<BaseWrapperProps> = ({
	children,
	description,
	error,
	id,
	label,
	required,
}) => {
	return (
		<ClayForm.Group
			className={classNames({
				'has-error': error,
			})}
		>
			{label && (
				<label
					className={classNames(
						'font-weight-bold mb-1 mx-0 text-paragraph',
						{required}
					)}
					htmlFor={id}
				>
					{label}
				</label>
			)}

			{children}

			{description && (
				<small className="form-text text-muted" id="Help">
					{description}
				</small>
			)}

			{error && <InputWarning>{error}</InputWarning>}
		</ClayForm.Group>
	);
};

export default BaseWrapper;
