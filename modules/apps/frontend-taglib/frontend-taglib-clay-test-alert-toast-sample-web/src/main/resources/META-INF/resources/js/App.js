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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {openToast} from 'frontend-js-web';
import React from 'react';

import '../css/main.scss';

export default () => {
	const onClickSuccess = () => {
		openToast({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			title: Liferay.Language.get('success'),
			type: 'success',
		});
	};

	const onClickFail = () => {
		openToast({
			message: Liferay.Language.get('an-unexpected-error-occurred'),
			title: Liferay.Language.get('error'),
			type: 'danger',
		});
	};

	return (
		<div>
			<ClayAlert title="Info">
				This widget is used to test out Clay Toast Alert.
			</ClayAlert>

			<div className="sheet-footer">
				<ClayButton.Group spaced>
					<ClayButton onClick={onClickSuccess} type="submit">
						{Liferay.Language.get('success-submit')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={onClickFail}
						type="submit"
					>
						{Liferay.Language.get('fail-submit')}
					</ClayButton>
				</ClayButton.Group>
			</div>
		</div>
	);
};
