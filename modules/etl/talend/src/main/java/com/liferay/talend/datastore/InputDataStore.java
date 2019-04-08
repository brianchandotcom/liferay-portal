package com.liferay.talend.datastore;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Checkable;
import org.talend.sdk.component.api.configuration.condition.ActiveIf;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.DefaultValue;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

/**
 * @author Igor Beslic
 */
@Checkable("checkInputDataStore")
@DataStore("InputDataStore")
@Documentation("Aggregator data store for authentication stores")
@GridLayout(
	{
		@GridLayout.Row({"authenticationMethod"}),
		@GridLayout.Row({"basicDataStore"}), @GridLayout.Row({"oAuthDataStore"})
	}
)
public class InputDataStore {

	public AuthenticationMethod getAuthenticationMethod() {
		return authenticationMethod;
	}

	public BasicDataStore getBasicDataStore() {
		return basicDataStore;
	}

	public OAuthDataStore getoAuthDataStore() {
		return oAuthDataStore;
	}

	public InputDataStore setAuthenticationMethod(
		AuthenticationMethod authenticationMethod) {

		this.authenticationMethod = authenticationMethod;

		return this;
	}

	public InputDataStore setBasicDataStore(BasicDataStore dataStore) {
		this.basicDataStore = dataStore;

		return this;
	}

	public InputDataStore setoAuthDataStore(OAuthDataStore oAuthDataStore) {
		this.oAuthDataStore = oAuthDataStore;

		return this;
	}

	@DefaultValue("BASIC")
	@Documentation("Authentication Method")
	@Option
	@Required
	private AuthenticationMethod authenticationMethod;

	@ActiveIf(target = "authenticationMethod", value = "BASIC")
	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private BasicDataStore basicDataStore;

	@ActiveIf(target = "authenticationMethod", value = "OAUTH2")
	@Documentation("OAuth2 Data Store")
	@Option
	private OAuthDataStore oAuthDataStore;

}