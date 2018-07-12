import {portlet} from './portlet_data.es';

function fetchMock(data) {
	global.fetch = jest.fn().mockImplementation(
		() => {
			return Promise.resolve(
				{
					text: jest.fn().mockImplementation(() => Promise.resolve(JSON.stringify(data)))
				}
			);
		}
	);
}

global.fetchMock = fetchMock;
global.portlet = Object.assign({}, portlet);