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

import ClayAlert from '@clayui/alert';
import ClayIcon from '@clayui/icon';

import getIconSpriteMap from '../../utils/getIconSpriteMap';

type AlertProps = {
	closeAlert: boolean;
	displayType: string | any;
	setShowAlert: Function;
	title: string;
};

const Alert = ({closeAlert, displayType, setShowAlert, title}: AlertProps) => {
	return (
		<ClayAlert
			displayType={displayType}
			spritemap={getIconSpriteMap()}
			title={title}
		>
			No entries were found
			{closeAlert && (
				<ClayIcon
					onClick={() => {
						setShowAlert(false);
					}}
					spritemap={getIconSpriteMap()}
					symbol="times-small"
				/>
			)}
		</ClayAlert>
	);
};
export default Alert;
