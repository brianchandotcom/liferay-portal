import ReactDOM from 'react-dom';

import HelloBar from './routes/hello-bar/pages/HelloBar';
import HelloFoo from './routes/hello-foo/pages/HelloFoo';
import HelloWorld from './routes/hello-world/pages/HelloWorld';
import {HashRouter, Route, Routes} from 'react-router-dom';
import './common/styles/index.scss';

const App = () => {
	return (
		<HashRouter>
			<Routes>
				<Route index element={<HelloWorld />} />
				<Route path="/hello-foo" element={<HelloFoo />} />
				<Route path="/hello-bar" element={<HelloBar />} />
			</Routes>
		</HashRouter>
	);
};

class WebComponent extends HTMLElement {
	connectedCallback() {
		ReactDOM.render(<App />, this);
	}
}

const ELEMENT_ID = 'liferay-remote-app-testray';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
