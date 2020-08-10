import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

window.Liferay = {
	Language: {
		get: key => key
	}
};

configure({ adapter: new Adapter() });
