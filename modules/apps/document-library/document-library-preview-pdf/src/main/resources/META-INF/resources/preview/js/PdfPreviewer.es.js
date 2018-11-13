import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './PdfPreviewer.soy';


/**
 * Component that create an pdf preview
 * @review
 */
class PdfPreviewer extends Component {
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
PdfPreviewer.STATE = {
};

Soy.register(PdfPreviewer, templates);
export {PdfPreviewer};
export default PdfPreviewer;