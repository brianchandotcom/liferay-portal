import ClayButton from '@clayui/button';
import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

const SIZES = ['lg', 'sm'];

const INSET_POSITIONS = ['after', 'before'];
const APPEND_POSITIONS = ['append', 'prepend'];

const NUMBER_MAX_LENGTH = 19;

class InputText extends React.Component {
	render() {
		return (
			<div
				className={`input-group-text${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				{this.props.children}
			</div>
		);
	}
}

class InputButton extends React.Component {
	static propTypes = {
		position: PropTypes.oneOf(APPEND_POSITIONS).isRequired
	};

	render() {
		const {className, position, ...otherProps} = this.props;

		return (
			<InputGroupItem className={className} position={position} shrink>
				<ClayButton
					{...omitDefinedProps(otherProps, InputButton.propTypes)}
				>
					{this.props.children}
				</ClayButton>
			</InputGroupItem>
		);
	}
}

class InputInset extends React.Component {
	static propTypes = {
		position: PropTypes.oneOf(INSET_POSITIONS)
	};

	render() {
		const {className, position} = this.props;

		const classes = getCN('input-group-inset-item', className, {
			[`input-group-inset-item-${position}`]: position
		});

		return <div className={classes}>{this.props.children}</div>;
	}
}

class InputGroupItem extends React.Component {
	static defaultProps = {
		shrink: false
	};

	static propTypes = {
		position: PropTypes.oneOf(APPEND_POSITIONS),
		shrink: PropTypes.bool
	};

	render() {
		const {className, position, shrink} = this.props;

		const classes = getCN('input-group-item', className, {
			'input-group-item-shrink': shrink,
			[`input-group-${position}`]: position
		});

		return <div className={classes}>{this.props.children}</div>;
	}
}

class InputGroup extends React.Component {
	static propTypes = {
		size: PropTypes.oneOf(SIZES)
	};

	render() {
		const {children, className, size, ...otherProps} = this.props;

		const classes = getCN('input-group', className, {
			[`input-group-${size}`]: size
		});

		return (
			<div
				{...omitDefinedProps(otherProps, InputGroup.propTypes)}
				className={classes}
			>
				{children}
			</div>
		);
	}
}

class Input extends React.Component {
	static defaultProps = {
		type: 'text'
	};

	static propTypes = {
		checked: PropTypes.string,
		inset: PropTypes.oneOf(INSET_POSITIONS),
		size: PropTypes.oneOf(SIZES),
		type: PropTypes.string,
		value: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
	};

	constructor(props) {
		super(props);
		this._elementRef = React.createRef();
	}

	/**
	 * Public method used for focusing the input element.
	 */
	focus() {
		this._elementRef.current.focus();
	}

	/**
	 * Public method used for blurring the input element.
	 */
	blur() {
		this._elementRef.current.blur();
	}

	/**
	 * Public method used for retrieving value of input.
	 */
	getValue() {
		return this._elementRef.current.value;
	}

	/**
	 * Public method used to select all text in input.
	 */
	selectAll() {
		this._elementRef.current.select();
	}

	render() {
		const {
			checked,
			className,
			inset,
			size,
			type,
			value,
			...otherProps
		} = this.props;

		const classes = getCN('input-root', 'form-control', className, {
			['input-group-inset']: inset,
			[`input-group-inset-${inset}`]: inset,
			[`form-control-${size}`]: size
		});

		const ComponentFn = type === 'textarea' ? type : 'input';

		return (
			<ComponentFn
				{...omitDefinedProps(otherProps, Input.propTypes)}
				// TODO: Is defaultChecked required here? should we be using this for checkbox?
				className={classes}
				defaultChecked={checked}
				ref={this._elementRef}
				type={type}
				value={
					type === 'number' && value
						? String(value).slice(0, NUMBER_MAX_LENGTH)
						: value
				}
			/>
		);
	}
}

Input.Text = InputText;
Input.Group = InputGroup;
Input.GroupItem = InputGroupItem;
Input.Button = InputButton;
Input.Inset = InputInset;
Input.SIZES = SIZES;
export default Input;
