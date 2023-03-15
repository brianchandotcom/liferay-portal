@spira_Accounts @Accounts @team_FARO @priority_5
Feature: Assert an Account's Associated Individuals
	As an Business User, I should be able to see a list of individuals associated with the account

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert Individuals Associated with an Account
		Given I go to the "Accounts" page
		* I search for "Lesch, Walsh and Stracke"
		* I click "Lesch, Walsh and Stracke" in the table
		When I click the "Individuals" tab
		Then I should see the following users: Abram Bauch, Carey Anderson, Daniela Jacobson, Dannie Armstrong, Jeramy VonRueden