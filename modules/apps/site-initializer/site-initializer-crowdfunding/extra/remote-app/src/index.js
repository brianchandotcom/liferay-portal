import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';

class WebComponent extends HTMLElement {

  connectedCallback() {

       ReactDOM.render(<App />, this);
  }

}

const ELEMENT_ID = 'liferay-remote-app-crowdfunding';

if (!customElements.get(ELEMENT_ID)) {

  customElements.define(ELEMENT_ID, WebComponent);

}