@spira_Pagination @Pagination @Accounts @List @team_FARO @priority_3
Feature: Account activities list has pagination
	As a Business User, I should be able to paginate to the second page of account activities

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Paginate to second page of account activities
		Given I click "Schneider and Sons" in the table
		And I click the "Activities" tab
		When I go to page "2" in the creation modal
		Then I should be on page "2" in the creation modal
		And I should see a session named "Visited www.horacio-kulas.com" in the table