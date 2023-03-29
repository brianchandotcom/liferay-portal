@spira_Data_Source @Data_Source @Deletion @team_FARO @priority_4 @skip
Feature: Assert Unaffected Accounts Do Not Appear During Data Source Deletion
	As an Business User, I should not see unaffected Accounts during Data Source deletion

	Background: [Setup] Add a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Salesforce" button
		* I type "AssertUnaffectedAccounts - ${Random.1}" into the "Name" input field
		* I complete the Salesforce OAuth authorization
		* I should see that "AssertUnaffectedAccounts - ${Random.1}" was successfully authenticated
		* I should see that the Salesforce Accounts data is synced
		
	Scenario: Assert Unaffected Accounts Don't Appear During Data Source Deletion
		Given I go to the "Accounts" page
		And I search for "CT Test"
		And I should only see Accounts named "CT Test" in the table
		And I go to the "Data Source" page
		And I click "SALESFORCE-DATASOURCE-FARO-EXAMPLE" in the table
		When I click the "Delete Data Source" button
		Then I should see that "10 Accounts" will be affected on the Data Source deletion page
		When I click the affected "Accounts" on the Data Source deletion page
		Then I should see "Beier Group" in a table
		And I should not see an account named "CT Test" in the table