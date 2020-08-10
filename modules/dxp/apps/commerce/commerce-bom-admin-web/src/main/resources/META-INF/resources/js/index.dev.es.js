import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.es';

import { StoreProvider } from './components/StoreContext.es';

import apiEndpointDefinitions from '../../../../../../dev/apiEndpointDefinitions';

import 'clay-css/src/scss/atlas.scss';

import '../css/main.scss';

window.Liferay = {
    authToken: 'fakeToken'
};

const fakeData = {
    id: 'adminPartFinder',
    spritemap: '/test-icons.svg',
    areaId: 'asd',
    areaApiUrl: apiEndpointDefinitions.AREA,
    productApiUrl: apiEndpointDefinitions.PRODUCTS
};

ReactDOM.render(
    <StoreProvider>
        <App {...fakeData} />
    </StoreProvider>, 
    document.getElementById('root')
);
