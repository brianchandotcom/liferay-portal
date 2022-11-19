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

import classNames from 'classnames';
interface IProps {
	children?: React.ReactNode;
}

const Footer = ({
	children,
	className,
}: IProps & React.HTMLAttributes<HTMLDivElement>) => (
	<div
		className={classNames(
			'border-neutral-2 border-top d-md-flex mt-4 pt-4',
			className
		)}
	>
		{children}
	</div>
);

export default Footer;
