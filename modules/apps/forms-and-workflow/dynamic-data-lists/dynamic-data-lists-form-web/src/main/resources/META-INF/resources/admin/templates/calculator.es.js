import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './calculator.soy';

/**
 * Calculator Component
 */
class Calculator extends Component {}

// Register component
Soy.register(Calculator, templates, 'render');

export default Calculator;