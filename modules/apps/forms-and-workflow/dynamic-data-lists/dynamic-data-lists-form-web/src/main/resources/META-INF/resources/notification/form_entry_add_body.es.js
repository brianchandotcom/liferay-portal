import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './form_entry_add_body.soy';

/**
 * FormEntryAddBody Component
 */
class FormEntryAddBody extends Component {}

// Register component
Soy.register(FormEntryAddBody, templates, 'form_entry_add_body');

export default FormEntryAddBody;