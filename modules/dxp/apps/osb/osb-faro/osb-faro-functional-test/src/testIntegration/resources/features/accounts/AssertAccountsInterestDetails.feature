@spira_Accounts @Accounts @team_FARO @priority_3
Feature: Account interest details are shown
	As a Business User, I should be able to click into an accounts interest and details are shown

	Background: [Setup] Navigate to Accounts
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Murazik, Lesch and Hyatt" in the table

	Scenario: Click interest shows detail
		Given I click the "Interests" tab
		When I click "mesh" in the table
		Then I should see an individual named "Karlene Sipes" in the table