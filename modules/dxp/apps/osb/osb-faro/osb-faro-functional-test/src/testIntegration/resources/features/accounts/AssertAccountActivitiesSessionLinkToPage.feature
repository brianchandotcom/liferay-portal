@spira_Accounts @Accounts @team_FARO @priority_3
Feature: Session link to page in account activities
	As a Business User, I should be able to link to session page

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Link to session page in account activities
		Given I click the "Activities" tab
		And I click "Visited www.horacio-kulas.com" in the table
		When I click "target user-centric e-commerce" in the table
		Then I should see the Sites Page Overview Cards with their details