@spira_Accounts @Accounts @team_FARO @priority_3
Feature: Selecting a Segment in Accounts
	As a Business User, I should be able to select a segment in accounts

	Background: [Setup] Navigate to Accounts
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Select a Segment in Accounts
		Given I click the "Segments" tab
		When I click "engineers" in the table
		Then I should see the Segment Profile Cards with their details