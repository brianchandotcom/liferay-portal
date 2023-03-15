@spira_Accounts @Accounts @Profile @team_FARO @priority_5 @blocked
Feature: Assert the Account Overview Cards
	As an Business User, I should be able to assert the cards on the Overview tab of an Account's page

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
#		* I go to the "Data Source" page
#		* I click the "Add Data Source" button
#		* I click the "Salesforce" button
#		* I type "AssertAccountOverviewCards - ${Random.1}" into the "Name" input field
#		* I complete the Salesforce OAuth authorization
#		* I should see that "AssertAccountOverviewCards - ${Random.1}" was successfully authenticated
#		* I should see that the Salesforce Accounts data is synced

	Scenario: Assert the Account Overview Cards
		Given I go to the "Accounts" page
		And I search for "Kautzer, Kautzer and Kautzer"
		When I click "Kautzer, Kautzer and Kautzer" in the table
		Then I should see the Account Overview Cards with their details