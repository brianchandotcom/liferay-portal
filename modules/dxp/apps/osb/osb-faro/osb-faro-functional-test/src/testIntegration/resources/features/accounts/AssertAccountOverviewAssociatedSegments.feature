@spira_Accounts @Accounts @Profile @team_FARO @priority_3
Feature: Account overview associated segment show segment
	As a Business User, I should be able to assert segments account members belong to

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Assert segments account members belong to
		Given I click "Kertzmann, Kilback and Watsica" in the table
		Then I should see "everybody" in the card list "Associated Segments"