@spira_Accounts @Accounts @team_FARO @priority_5
Feature: Set a Custom Range time filter for Account Activities
	As an Business User, I should be able to set a Custom Range time filter for Account Activities

	Background: [Setup] Navigate to Accounts
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Set a Custom Range time filter for Account Activities
		Given I click the "Activities" tab
		And I click the "Last 30 days" dropdown in the card
		When I click the "Custom Range" button
		And I set start date "July 31 2020" and end date "August 11 2020"
		Then I should see time filter "Jul 31, 2020 - Aug 11, 2020" in card
		And I should see granularity starts "Jul 31" and ends "Aug 11" in "Account Activities" card