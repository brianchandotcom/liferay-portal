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

import getCN from 'classnames';
import Component, {Config} from 'metal-jsx';

const Header = ({children}) => {
	return <div class="popover-header">{children}</div>;
};

const Body = ({children}) => {
	return <div class="popover-body">{children}</div>;
};

const Footer = ({children}) => {
	return <div class="popover-footer">{children}</div>;
};

class PopoverBase extends Component {
	render() {
		const {children, placement, visible} = this.props;
		const classes = getCN('popover', {
			[`clay-popover-${placement}`]: placement,
			['hide']: !visible
		});

		return (
			<div {...this.otherProps()} class={classes}>
				{placement !== 'none' && <div class="arrow" />}
				{children}
			</div>
		);
	}
}

PopoverBase.PROPS = {
	/**
	 * @type {string}
	 * @default undefined
	 */
	placement: Config.oneOf(['bottom', 'left', 'none', 'right', 'top']).value(
		'none'
	),

	/**
	 * @type {boolean}
	 * @default false
	 */
	visible: Config.bool().value(false)
};

PopoverBase.Header = Header;
PopoverBase.Body = Body;
PopoverBase.Footer = Footer;

export {PopoverBase};
export default PopoverBase;
