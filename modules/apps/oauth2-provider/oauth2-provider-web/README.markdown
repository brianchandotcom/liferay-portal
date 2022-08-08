## OAuth2 JavaScript Client

This module defines an OAuth2 Client which when used in combination with
Liferay's OAuth2 User Agent Applications simplifies the JavaScript code
required to make calls to the Application's endpoint(s) (which are rooted at `homePageURL`).

### Steps

1. Follow the documentation to [create a OAuth2 User Agent based Application](https://learn.liferay.com/dxp/latest/en/headless-delivery/using-oauth2/creating-oauth2-applications.html?highlight=oauth2%20application) or define a configuration for factory PID `com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration`.
   Here is a sample file included in an extension, `uscities.client-extension-config.json`:

   ```json
   {
     "com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration~uscities": {
         "description": "US Cities Resource Server",
         "homePageURL": "https://$[conf:ext.lxc.liferay.com.mainDomain]"
     }
   }
   ```

1. Use the static constructor `FromUserAgentApplication` on the global `Liferay.OAuth2Client ` to create a client that can be (re)used to make authenticated and authorised calls to the endpoints   that can be verified against the Liferay DXP OAuth2 authorisation server.

  For example, given a **OAuth2 User Agent Application** with the name `uscities` (which could have been created from the extension configuration above):
  ```javascript
  Liferay.OAuth2Client.FromUserAgentApplication('uscities').fetch(
    '/some/path'
  ).then(
    r => r.text()
  ).then(
    r => console.log('success:', r)
  ).catch(
    e => console.log('error:', e)
  );
  ```

#### Details

1. The URI passed to `fetch` (as either `string` or `Request`) is only allowed to contain the base URL of the User Agent Application (a.k.a. `homePageURL`) or be relative to it. With this in mind it's best to pass URI without a base as demonstrated in the example in order to retain portability.

1. The client will _not_ trigger authentication. If the current user session is not authenticated an error is returned, such as `error: login_required`.

1. The client's token will be cached for re-use by multiple requests. Multiple clients may be instantiated against the same Application name and still benefit from cached tokens.

1. Tokens are only valid as long as the server session and are revoked on logout.