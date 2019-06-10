import compose from '../../util/compose.es';
import FormRenderer from '../../components/FormRenderer/FormRenderer.es';
import Soy from 'metal-soy';
import templates from '../../components/FormRenderer/FormRenderer.soy';
import withStore from '../../store/withStore.es';

const composed = compose(withStore)(FormRenderer);

Soy.register(composed, templates);

export default composed;
