import { render } from '@liferay/frontend-js-react-web';

import Diagram from './Diagram';

export default function (id, props) {
    const portletFrame = window.document.getElementById(id);

    render(Diagram, props, portletFrame);
}