import '../Text/Text.es';
import './PasswordRegister.soy.js';
import Soy from 'metal-soy';
import templates from './Password.soy.js';
import {Config} from 'metal-state';

class Password extends Text {
}

Soy.register(Password, templates);

export default Password;