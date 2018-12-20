import React, {Component, createRef} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '../shared/ClayButton.es';
import getCN from 'classnames';
import {ENTER} from 'utils/key-constants.es';

class TitleEditor extends Component {
	static propTypes = {
		inputName: PropTypes.string,
		onBlur: PropTypes.func,
		onChange: PropTypes.func.isRequired,
		placeholder: PropTypes.string,
		value: PropTypes.string
	};

	state = {
		editing: false
	};

	titleInput = createRef();

	_handleBlur = event => {
		const {onBlur} = this.props;

		this.setState({editing: false});

		if (onBlur) {
			onBlur(event);
		}
	}

	_handleEdit = event => {
		event.preventDefault();

		this.setState(
			{
				editing: !this.state.editing
			},
			() => this.titleInput.current.select()
		);
	};

	_handleKeyDown = event => {
		if (event.keyCode === ENTER) {
			this.titleInput.current.blur();
		}
	};

	render() {
		const {inputName, onChange, placeholder, value} = this.props;

		const {editing} = this.state;

		const inputClasses = getCN(
			'title-input',
			{
				'hide': !editing
			}
		);

		const displayClasses = getCN(
			'title-display',
			{
				'hide': editing,
				'placeholder-display': value === placeholder
			}
		);

		return (
			<div className="title-editor-root">
				<input
					autoFocus
					className={inputClasses}
					data-testid="title-input"
					name={inputName}
					onBlur={this._handleBlur}
					onChange={onChange}
					onKeyDown={this._handleKeyDown}
					placeholder={placeholder}
					ref={this.titleInput}
					type="text"
					value={value}
				/>

				<div className={displayClasses}>
					<span onClick={this._handleEdit}>
						{value}
					</span>

					<ClayButton
						borderless
						className="edit-icon"
						data-testid="edit-button"
						iconName="pencil"
						monospaced
						onClick={this._handleEdit}
					/>
				</div>
			</div>
		);
	}
}

export default TitleEditor;