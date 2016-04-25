import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import templates from './ImageEditorLoading.soy';

/**
 * ImageEditor Loading Component
 */
class ImageEditorLoading extends Component {}

// Register component
Soy.register(ImageEditorLoading, templates);

export default ImageEditorLoading;