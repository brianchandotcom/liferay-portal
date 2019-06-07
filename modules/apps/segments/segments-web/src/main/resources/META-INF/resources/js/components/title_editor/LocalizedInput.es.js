import getCN from 'classnames';
import LocalizedDropdown from './LocalizedDropdown.es';
import PropTypes from 'prop-types';
import React from 'react';

export default class LocalizedInput extends React.Component {
	static propTypes = {
		availableLanguages: PropTypes.object.isRequired,
		initialLanguageId: PropTypes.string.isRequired,
		initialOpen: PropTypes.bool,
		initialValues: PropTypes.object,
		onChange: PropTypes.func,
		placeholder: PropTypes.string,
		readOnly: PropTypes.bool
	};
	static defaultProps = {
		initialOpen: false,
		initialValues: {},
		onChange: () => {},
		placeholder: '',
		readOnly: false
	};

	constructor(props) {
		super(props);

		const {availableLanguages, initialLanguageId, initialValues} = props;
		this.state = {
			availableLanguages: Object.keys(availableLanguages).map(key => {
				const value = availableLanguages[key];

				return {
					hasValue: !!initialValues[key],
					key,
					value
				};
			}),
			currentLang: initialLanguageId,
			currentValue: initialValues[initialLanguageId] || '',
			values: initialValues
		};
	}

	_handleLanguageChange = langKey => {
		this.setState(prevState => ({
			currentLang: langKey,
			currentValue: prevState.values[langKey] || ''
		}));
	};

	_onChange = (event, hasError) => {
		this.props.onChange(event, this.state.values, hasError);
	};

	_handleInputChange = event => {
		event.persist();

		let hasError = false;

		this.setState(
			prevState => {
				const newValues = {
					...prevState.values,
					[prevState.currentLang]: event.target.value
				};

				hasError = !this._validateValues(newValues);

				return {
					availableLanguages: prevState.availableLanguages.map(
						lang => {
							let newLang = lang;
							if (lang.key === prevState.currentLang) {
								newLang = {
									...lang,
									hasValue: event.target.value !== ''
								};
							}
							return newLang;
						}
					),
					currentValue: event.target.value,
					hasError,
					values: newValues
				};
			},
			() => this._onChange(event, hasError)
		);
	};

	_validateValues(values) {
		const {defaultLang} = this.props;

		const parsedValue =
			values[defaultLang] && values[defaultLang].replace(/\s/g, '');

		return !!parsedValue;
	}

	render() {
		const {
			defaultLang,
			initialLanguageId,
			initialOpen,
			placeholder,
			readOnly
		} = this.props;

		const {availableLanguages, currentValue, hasError} = this.state;

		const inputGroupItemClasses = getCN('input-group-item ml-2', {
			'has-error': hasError
		});

		return (
			<div className='input-group input-localized input-localized-input'>
				<LocalizedDropdown
					availableLanguages={availableLanguages}
					defaultLang={defaultLang}
					initialLang={initialLanguageId}
					initialOpen={initialOpen}
					onLanguageChange={this._handleLanguageChange}
				/>
				<div className={inputGroupItemClasses}>
					<input
						className='rounded form-control language-value field form-control-inline form-control'
						data-testid='localized-main-input'
						onChange={this._handleInputChange}
						placeholder={placeholder}
						readOnly={readOnly}
						type='text'
						value={currentValue}
					/>
				</div>
			</div>
		);
	}
}
