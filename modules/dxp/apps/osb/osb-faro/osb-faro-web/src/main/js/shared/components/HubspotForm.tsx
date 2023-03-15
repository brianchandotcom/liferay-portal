import React from 'react';

const HUBSPOT_ENDPOINT = '//js.hsforms.net/forms/v2.js';
const HUBSPOT_ID = 'hubspotForm';

const mockJQuery: () => void = () => {
	window.jQuery =
		window.jQuery ||
		(node => {
			if (typeof node == 'string') {
				return document.querySelector(node);
			}

			return {
				change: () => {},
				trigger: () => {}
			};
		});
};

const loadScript: () => HTMLElement = () => {
	mockJQuery();

	const script = document.createElement('script');
	script.src = HUBSPOT_ENDPOINT;

	document.head.appendChild(script);

	return script;
};

const createForm: (
	props: React.Props<React.HTMLAttributes<HTMLElement>>
) => void = props =>
	window.hbspt.forms.create({
		...props,
		target: `#${HUBSPOT_ID}`
	});

interface IHubspotFormProps extends React.HTMLAttributes<HTMLElement> {
	css?: string;
	cssClass?: string;
	cssRequired?: string;
	formId: string;
	onFormSubmitted?: () => void;
	portalId: string;
	submitButtonClass?: string;
}

const HubspotForm: React.FC<IHubspotFormProps> = props => {
	if (!window.hbspt) {
		const script = loadScript();

		script.onload = () => {
			createForm(props);
		};
	} else {
		createForm(props);
	}

	return <div id={HUBSPOT_ID} />;
};

export default HubspotForm;
