import React from "react";
import ReactDOM from "react-dom";

import { WebComponent } from "~/common/WebComponent";
import ClayProvider from "~/common/providers/ClayProvider";

import SharedStyle from "~/common/styles/global.scss";
import CustomerPortal from "~/routes/customer-portal";
import Onboarding from "~/routes/onboarding";

const CustomerPortalApplication = ({ application }) => {
  const SearchParams = new URLSearchParams(window.location.search);

  const route = SearchParams.get("customer_dev_application") || application;

  if (route === "portal") {
    return <CustomerPortal />;
  }

  if (route === "onboarding") {
    return <Onboarding />;
  }
};

class DirectToCustomerWebComponent extends WebComponent {
  connectedCallback() {
    super.connectedCallback(SharedStyle);

    ReactDOM.render(
      <ClayProvider>
        <CustomerPortalApplication
          application={super.getAttribute("application")}
        />
      </ClayProvider>,
      this.mountPoint
    );
  }
}

const ELEMENT_ID = "liferay-customer-portal";

if (!customElements.get(ELEMENT_ID)) {
  customElements.define(ELEMENT_ID, DirectToCustomerWebComponent);
}
