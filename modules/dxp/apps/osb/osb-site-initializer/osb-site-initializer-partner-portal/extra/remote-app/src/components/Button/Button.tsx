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

import ClayButton from '@clayui/button';
import ClayIcon, {ClayIconSpriteContext} from '@clayui/icon';

import getIconSpriteMap from '../../utils/getIconSpriteMap';

type Props = {
	className: string;
	displayType: string;
	icon: string;
	label: string;
	onClick: any;
	type: string;
};

const Button = ({icon, label, ...props}: Props) => {
	return (
		<>
			<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
				<ClayButton {...props}>
					{icon && <ClayIcon className="mr-1" symbol={icon} />}

					{label}
				</ClayButton>
			</ClayIconSpriteContext.Provider>
		</>
	);
};

export default Button;
