#if (!(${liferayVersion.startsWith("7.0")} && ${liferayVersion.startsWith("7.1")} && ${liferayVersion.startsWith("7.2")}))
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type';
import React, {useState} from 'react';
#elseif (!(${liferayVersion.startsWith("7.0")} || ${liferayVersion.startsWith("7.1")}))
import 'dynamic-data-mapping-form-field-type/FieldBase/FieldBase.es';
import './${artifactId}Register.soy.js';
import templates from './${artifactId}.soy.js';
import {Config} from 'metal-state';
#end

#if (${liferayVersion.startsWith("7.0")} || ${liferayVersion.startsWith("7.1")} || ${liferayVersion.startsWith("7.2")})
import Component from 'metal-component';
import Soy from 'metal-soy';
#end

#if (!(${liferayVersion.startsWith("7.0")} && ${liferayVersion.startsWith("7.1")} && ${liferayVersion.startsWith("7.2")}))
export default function ${className}({
								   label,
								   name,
								   onChange,
								   predefinedValue,
								   readOnly,
								   value,
								   ...otherProps
							   }) {
	const [currentValue, setCurrentValue] = useState(
		value ? value : predefinedValue
	);

	return <FieldBase
			label={label}
			name={name}
			predefinedValue={predefinedValue}
			{...otherProps}
		>
			<input
				className="ddm-field-slider form-control ${className}"
				disabled={readOnly}
				id="myRange"
				max={100}
				min={1}
				name={name}
				onInput={(event) => {
					setCurrentValue(event.target.value);
					onChange(event);
				}}
				type="range"
				value={currentValue ? currentValue : predefinedValue}
            />
		</FieldBase>
}

#elseif (${liferayVersion.startsWith("7.0")} || ${liferayVersion.startsWith("7.1")})
import templates from './${artifactId}.soy';

/**
 * ${className} Component
 */
class ${className} extends Component {}

// Register component
Soy.register(${className}, templates, 'render');

if (!window.DDM${className}) {
	window.DDM${className} = {

	};
}

window.DDM${className}.render = ${className};
#else
/**
 * ${className} Component
 */
class ${className} extends Component {

	dispatchEvent(event, name, value) {
		this.emit(name, {
			fieldInstance: this,
			originalEvent: event,
			value
		});
	}

	_handleFieldChanged(event) {
		const {value} = event.target;

		this.setState(
			{
				value
			},
			() => this.dispatchEvent(event, 'fieldEdited', value)
		);
	}
}

${className}.STATE = {

	name: Config.string().required(),

	predefinedValue: Config.oneOfType([Config.number(), Config.string()]),

	required: Config.bool().value(false),

	showLabel: Config.bool().value(true),

	spritemap: Config.string(),

	value: Config.string().value('')
}

// Register component
Soy.register(${className}, templates);
#end

#if (${liferayVersion.startsWith("7.0")} || ${liferayVersion.startsWith("7.1")} || ${liferayVersion.startsWith("7.2")})
export default ${className};
#end