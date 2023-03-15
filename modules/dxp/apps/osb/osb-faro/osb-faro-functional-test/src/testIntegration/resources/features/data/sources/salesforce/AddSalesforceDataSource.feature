@spira_Data_Source @Data_Source @Salesforce @team_FARO @priority_5 @blocked
Feature: Add a Salesforce Data Source
	As an Business User, I should be able to connect a Salesforce Data Source using OAuth2

	Background: [Setup] Navigate to the Data Source Page and Click to Add a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button

	Scenario: Add a Salesforce Data Source using OAuth2
		Given I click the "Salesforce" button
		And I type "AddSalesforceDataSource - ${Random.1}" into the "Name" input field
		When I complete the Salesforce OAuth authorization
		Then I should see that "AddSalesforceDataSource - ${Random.1}" was successfully authenticated