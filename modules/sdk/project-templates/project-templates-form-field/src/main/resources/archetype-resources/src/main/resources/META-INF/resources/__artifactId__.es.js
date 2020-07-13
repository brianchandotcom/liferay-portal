#if (${liferayVersion.startsWith("7.2")} || ${liferayVersion.startsWith("7.3")})
import 'dynamic-data-mapping-form-field-type/FieldBase/FieldBase.es';
import './${artifactId}Register.soy.js';
import templates from './${artifactId}.soy.js';
import {Config} from 'metal-state';
#end
import Component from 'metal-component';
import Soy from 'metal-soy';

#if (!(${liferayVersion.startsWith("7.2")} || ${liferayVersion.startsWith("7.3")}))
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
export default ${className};