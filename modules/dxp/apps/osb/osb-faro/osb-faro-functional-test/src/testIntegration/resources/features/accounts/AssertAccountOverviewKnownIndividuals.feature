@spira_Accounts @Accounts @Profile @team_FARO @priority_3
Feature: Account overview known individuals show individuals
	As a Business User, I should be able to assert individuals belong to account

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Assert individuals belong to account
		Given I click "Schneider and Sons" in the table
		Then I should see "Maria Rau" in the card list "Known Individuals"