import './PasswordRegister.soy.js';
import Soy from 'metal-soy';
import templates from './Password.soy.js';
import Text from '../Text/Text.es';

class Password extends Text {}

Soy.register(Password, templates);

export default Password;
