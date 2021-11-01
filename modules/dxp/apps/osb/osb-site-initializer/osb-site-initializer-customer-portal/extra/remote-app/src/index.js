import React from "react";
import ReactDOM from "react-dom";

import { WebComponent } from "~/common/WebComponent";
import ClayProvider from "~/common/providers/ClayProvider";

import SharedStyle from "~/common/styles/global.scss";
import CustomerPortal from "~/routes/customer-portal";
import Onboarding from "~/routes/onboarding";

const CustomerPortalApplication = ({ liferaywebdavurl, route }) => {
  const SearchParams = new URLSearchParams(window.location.search);

  const routeEntry = SearchParams.get("customer_dev_application") || route;

  if (routeEntry === "portal") {
    return <CustomerPortal />;
  }

  if (routeEntry === "onboarding") {
    return <Onboarding assetsPath={liferaywebdavurl} />;
  }
};

class DirectToCustomerWebComponent extends WebComponent {
  connectedCallback() {
    super.connectedCallback(SharedStyle);

    ReactDOM.render(
      <ClayProvider>
        <CustomerPortalApplication
          liferaywebdavurl={super.getAttribute("liferaywebdavurl")}
          route={super.getAttribute("route")}
        />
      </ClayProvider>,
      this.mountPoint
    );
  }
}

const ELEMENT_ID = "liferay-remote-app-customer-portal";

if (!customElements.get(ELEMENT_ID)) {
  customElements.define(ELEMENT_ID, DirectToCustomerWebComponent);
}
