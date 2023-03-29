import Promise from 'metal-promise';

let responseData;

function setResponseData(data) {
	responseData = data;
}

function clearResponseData() {
	responseData = undefined;
}

const sendRequest = jest.fn(() => Promise.resolve(responseData));

sendRequest.setResponseData = setResponseData;
sendRequest.clearResponseData = clearResponseData;

export default sendRequest;
