import Component, {Config} from 'metal-jsx';
import getCN from 'classnames';

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
	static PROPS = {

		/**
		 * @type {string}
		 * @default undefined
		 */
		placement: Config.oneOf(
			[
				'bottom',
				'left',
				'none',
				'right',
				'top'
			]
		).value('none'),

		/**
		 * @type {boolean}
		 * @default false
		 */
		visible: Config.bool().value(false)
	};

	render() {
		const {children, placement, visible} = this.props;
		const classes = getCN(
			'popover',
			{
				[`clay-popover-${placement}`]: placement,
				['hide']: !visible
			}
		);

		return (
			<div {...this.otherProps()} class={classes}>
				{placement !== 'none' && <div class="arrow" />}
				{children}
			</div>
		);
	}
}

PopoverBase.Header = Header;
PopoverBase.Body = Body;
PopoverBase.Footer = Footer;

export {PopoverBase};
export default PopoverBase;