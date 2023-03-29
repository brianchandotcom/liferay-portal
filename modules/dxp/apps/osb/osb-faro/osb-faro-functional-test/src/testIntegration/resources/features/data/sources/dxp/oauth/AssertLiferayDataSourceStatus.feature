@spira_Data_Source @Data_Source @DXP @team_FARO @priority_3
Feature: Assert the Liferay DXP Data Source Status
	As an Business User, I should see the proper status for different DXP Data Source configurations

	Background: [Setup] Navigate to the Data Source Page and Click to Add a Data Source
		* I set up the local DXP instance
		* I add an OAuth2 application on the local DXP instance
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Liferay DXP" button
		* I click the "Connect with OAuth" button
		* I type "${NGROK_URL}/" into the "URL" input field
		* I type "${OAUTH2_CLIENT_ID}" into the "Consumer Key/Client ID" input field
		* I type "${OAUTH2_SECRET}" into the "Consumer Secret/Client Secret" input field

	Scenario: Configure DXP Data Source - Authentication Only
		Given I type "AssertLiferayDataSourceStatus (Auth Only) - ${Random.1}" into the "Name" input field
		And I click the "Authorize & Save" button
		When I accept the Liferay DXP OAuth2 Popup
		And I should see that "AssertLiferayDataSourceStatus (Auth Only) - ${Random.1}" was successfully authenticated
		And I go to the "Data Source" page
		Then I should see that the "AssertLiferayDataSourceStatus (Auth Only) - ${Random.1}" data source status is currently "Action Needed"

	Scenario: Configure DXP Data Source - Contacts Only
		Given I type "AssertLiferayDataSourceStatus (Contacts Only) - ${Random.1}" into the "Name" input field
		And I click the "Authorize & Save" button
		When I accept the Liferay DXP OAuth2 Popup
		And I should see that "AssertLiferayDataSourceStatus (Contacts Only) - ${Random.1}" was successfully authenticated
		And I click the "Configure Data Source" tab
		And I click the button for Contacts Configuration
		And I click the Sync All toggle switch
		And I click the "Save and Continue" button
		And I click the "Configure" button
		And I go to the "Data Source" page
		Then I should see that the "AssertLiferayDataSourceStatus (Contacts Only) - ${Random.1}" data source status is currently "Action Needed"

	Scenario: Configure DXP Data Source - Analytics Only
		Given I type "AssertLiferayDataSourceStatus (Analytics Only) - ${Random.1}" into the "Name" input field
		And I click the "Authorize & Save" button
		When I accept the Liferay DXP OAuth2 Popup
		And I should see that "AssertLiferayDataSourceStatus (Analytics Only) - ${Random.1}" was successfully authenticated
		And I click the "Configure Data Source" tab
		And I click the button for Analytics Configuration
		And I click the select all checkbox in the toolbar
		And I click the "Configure" button
		And I go to the "Data Source" page
		Then I should see that the "AssertLiferayDataSourceStatus (Analytics Only) - ${Random.1}" data source status is currently "Action Needed"
